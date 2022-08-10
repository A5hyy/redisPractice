package com.practice.springbootpractice.controller;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.practice.springbootpractice.models.Converter;
import com.practice.springbootpractice.models.Currency;
import com.practice.springbootpractice.service.RedisService;

@Controller
//@RequestMapping(path="/")
public class CurrencyController {

    RestTemplate template = new RestTemplate(); //like postman
    
    @Autowired
    RedisService service;
    @GetMapping("/")
    public String getHome(@ModelAttribute Currency curr){

        return "index";
    }

    @PostMapping("/showCurrency")
    public String showCurrency(@ModelAttribute Currency curr, Model model){
        String url = "https://api.apilayer.com/fixer/convert?to=" + curr.getTo() + "&from=" + curr.getFrom() + "&amount=" + curr.getAmount() ;
        String apiKey = System.getenv("API_KEY");
        //like postman
        RequestEntity<Void> request = RequestEntity.get(url)
                                        .header("apikey", apiKey)
                                        .accept(MediaType.APPLICATION_JSON)
                                        .build();
        ResponseEntity<Converter> response = template.exchange(request, Converter.class);
        // like postman

        Converter convert = response.getBody();

        service.save(convert);
        

        model.addAttribute("to", curr.getTo()); 
        model.addAttribute("from", curr.getFrom());
        model.addAttribute("amount", curr.getAmount());

        model.addAttribute("success", convert.getSuccess());
        model.addAttribute("query", convert.getQuery());
        model.addAttribute("info", convert.getInfo());
        model.addAttribute("date", convert.getDate());
        model.addAttribute("result", convert.getResult());
        

        return "exchange";
    }
    @GetMapping("/showCurrency/{id}")
    public String showId(Model model, @PathVariable(value="id")String id){
        Converter redisLoad = service.load(id);
        model.addAttribute("success", redisLoad.getSuccess());
        model.addAttribute("query", redisLoad.getQuery());
        model.addAttribute("info", redisLoad.getInfo());
        model.addAttribute("date", redisLoad.getDate());
        model.addAttribute("result", redisLoad.getResult());
        return "return";
    }
}


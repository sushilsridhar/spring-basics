package com.springbasics.hello;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/world")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("/you")
    public String helloYou(@RequestParam("name") String name) {
        return "Hello " + name + "!";
    }

    @GetMapping("/U/{name}")
    public String helloU(@PathVariable("name") String name) {
        return "Hello: PathParam " + name + "!";
    }
}

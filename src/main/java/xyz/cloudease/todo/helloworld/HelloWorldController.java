package xyz.cloudease.todo.helloworld;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5520")
@RestController
public class HelloWorldController {

    @GetMapping(path = "/hello-world")
    public HelloWorldBean helloWorld() {
        return new HelloWorldBean("Hello World");
    }

    @GetMapping(path = "/hello-world/{name}")
    public HelloWorldBean helloWorldWithPath(@PathVariable String name) {
        return new HelloWorldBean("Hello " + name);
    }
}

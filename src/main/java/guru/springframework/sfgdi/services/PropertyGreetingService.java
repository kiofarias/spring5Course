package guru.springframework.sfgdi.services;

public class PropertyGreetingService implements GreetingService{
    @Override
    public String sayGreeting() {
        return "Hello World! - Property";
    }
}

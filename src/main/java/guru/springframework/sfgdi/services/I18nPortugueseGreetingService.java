package guru.springframework.sfgdi.services;

public class I18nPortugueseGreetingService implements GreetingService {
    @Override
    public String sayGreeting() {
        return "Olá Mundo! - PT-BR";
    }
}

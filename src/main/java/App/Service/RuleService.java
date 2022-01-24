package App.Service;

import App.Entity.Order;
import App.Repository.CustomRuleRepo;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.Message;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleService {

    public static KieContainer kieContainer;

    @Autowired
    private CustomRuleRepo ruleRepository;

    public  void reload(){
        KieContainer kieContainer=loadContainerFromString(loadRules());
        this.kieContainer=kieContainer;
    }

    private List<Order> loadRules(){
        List<Order> rules= (List<Order>) ruleRepository.findAll();
//        System.out.println(rules.toString());
        return rules;
    }

    private  KieContainer loadContainerFromString(List<Order> rules) {
        long startTime = System.currentTimeMillis();
        KieServices ks = KieServices.Factory.get();
        KieRepository kr = ks.getRepository();
        KieFileSystem kfs = ks.newKieFileSystem();

        for (Order rule:rules) {
            int  drl=rule.getDiscount();
            String drl2 = rule.getCardType();
            kfs.write("src/main/resources/" + drl2.hashCode() + ".drl", String.valueOf(drl));
        }

        KieBuilder kb = ks.newKieBuilder(kfs);

        kb.buildAll();
        if (kb.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException("Build Errors:\n" + kb.getResults().toString());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time to build rules : " + (endTime - startTime)  + " ms" );
        startTime = System.currentTimeMillis();
        KieContainer kContainer = ks.newKieContainer(kr.getDefaultReleaseId());
        endTime = System.currentTimeMillis();
        System.out.println("Time to load container: " + (endTime - startTime)  + " ms" );
        return kContainer;
    }
}

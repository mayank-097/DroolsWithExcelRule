package App.Config;

import java.io.File;
import java.io.IOException;

import org.kie.api.io.ResourceType;
import org.kie.api.io.Resource;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration
public class DroolConfig {
	private KieServices kieServices = KieServices.Factory.get();

	Resource resource = null;
	File file = null;

	private KieFileSystem getKieFileSystem() throws IOException {
		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
		kieFileSystem.write(ResourceFactory.newClassPathResource("offer.xls"));

		//file = loadFile("DiscountCalculation.drl");
		//file = loadFile("ruleCalculation.drl");
		//resource = kieServices.getResources().newFileSystemResource(file).setResourceType(ResourceType.DRL);

		//kieFileSystem.write(resource);
		return kieFileSystem;

	}



	@Bean
	public KieContainer getKieContainer() throws IOException {
		System.out.println("Container created...");
		getKieRepository();
		KieBuilder kb = kieServices.newKieBuilder(getKieFileSystem());
		kb.buildAll();
		KieModule kieModule = kb.getKieModule();
		KieContainer kContainer = kieServices.newKieContainer(kieModule.getReleaseId());
		return kContainer;

	}

	private void getKieRepository() {
		final KieRepository kieRepository = kieServices.getRepository();
		kieRepository.addKieModule(new KieModule() {
			public ReleaseId getReleaseId() {
				return kieRepository.getDefaultReleaseId();
			}
		});
	}

	@Bean
	public KieSession getKieSession() throws IOException {
		System.out.println("session created...");
		return getKieContainer().newKieSession();

	}

	public static File loadFile(String fileName) {

		File loadfile = new File(DroolConfig.class.getClassLoader().getResource(fileName).getFile());
		return loadfile;
	}

}

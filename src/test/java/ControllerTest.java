import static org.springframework.restdocs.RestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.restdocs.config.RestDocumentationConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ControllerTest.Config.class})
@WebAppConfiguration
public class ControllerTest {

	protected MockMvc mockMvc;
	@Autowired
	protected WebApplicationContext context;

	@Configuration
	//@ComponentScan(basePackageClasses = ControllerTest.class)
	static class Config {
		@Bean Controller controller() {
			return new Controller();
		}
	}
	@Before
	public void setUp() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(this.context)
				.apply(new RestDocumentationConfigurer().withPort(8888))
				.addFilters(filter)
				.build();
	}

	@org.junit.Test
	public void testFindAll() throws Exception {
	mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andDo(document("dossier-get-example")
			)
	;
	}
}
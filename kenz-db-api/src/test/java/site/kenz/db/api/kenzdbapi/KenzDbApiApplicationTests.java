package site.kenz.db.api.kenzdbapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoDbUtils;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KenzDbApiApplicationTests {

	@Autowired
	private MongoDbUtils mongoDbUtils;

	@Test
	public void contextLoads() {
	}
}

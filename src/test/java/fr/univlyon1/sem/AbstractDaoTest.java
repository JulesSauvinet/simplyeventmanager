package fr.univlyon1.sem;


import fr.univlyon1.sem.bean.ConfigBean;
import fr.univlyon1.sem.dao.EntityDao;
import fr.univlyon1.sem.dao.UserDao;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@WebAppConfiguration
@ContextConfiguration(locations = {"/config/applicationContext-test.xml"})
public abstract class AbstractDaoTest extends AbstractTransactionalJUnit4SpringContextTests {


    @Autowired(required = true)
    protected UserDao userDao;
    @Autowired(required = true)
    protected EntityDao entityDao;
    @Autowired(required=true)
    protected WebApplicationContext context;
    @Autowired
    protected ConfigBean config;


    final Logger logger = LoggerFactory.getLogger(AbstractDaoTest.class);
}
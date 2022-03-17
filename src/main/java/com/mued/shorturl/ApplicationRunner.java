package com.mued.shorturl;

import com.mued.shorturl.models.UrlMapping;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationRunner implements CommandLineRunner {

    private MongoTemplate mongoTemplate;

    public ApplicationRunner(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(String... args) throws Exception {

        UrlMapping emptyUrlMapping = new UrlMapping("emptyURL");
        this.mongoTemplate.insert(emptyUrlMapping);
        List<UrlMapping> urls = mongoTemplate.findAll(UrlMapping.class);

        System.out.println("Application started ..!");
        System.out.println("Obj: " + urls.get(0).toString());

        Query all = Query.query(Criteria.where("url").is("emptyURL"));
        mongoTemplate.findAllAndRemove(all,
                UrlMapping.class);
    }
}

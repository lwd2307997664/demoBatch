/**
 * FileName: ExcelBatchConfig
 * Author:   linwd
 * Date:     2021/5/5 14:07
 * Description: 批量导入数据
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.yangxf.demoBatch.config;

import com.yangxf.demoBatch.entity.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

/**
 * 〈一句话功能简述〉<br> 
 * 〈批量导入数据〉
 *
 * @author linwd
 * @create 2021/5/5
 * @since 1.0.0
 */
@Configuration
public class ExcelBatchConfig {

    @Autowired
    JobBuilderFactory jobBuilderFactory;
    @Autowired
    StepBuilderFactory stepBuilderFactory;
    @Autowired
    DataSource dataSource;

    @Bean
    @StepScope
    FlatFileItemReader <User>  itemReader(){
        FlatFileItemReader<User> reader=new FlatFileItemReader<>();
        reader.setLinesToSkip(1);
        reader.setEncoding("UTF-8");
        reader.setResource(new ClassPathResource("demo.csv"));
        reader.setLineMapper(new DefaultLineMapper<User>(){{
            setLineTokenizer(new DelimitedLineTokenizer(){{
                setNames("id","username","sex","addr");
                setDelimiter(",");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<User>(){{
                setTargetType(User.class);
            }});
        }});
        return reader;
    }

    @Bean
    JdbcBatchItemWriter jdbcBatchItemWriter(){
        JdbcBatchItemWriter writer=new JdbcBatchItemWriter();
        writer.setDataSource(dataSource);
        writer.setSql("insert into t_user (id,username,sex,addr) values (:id,:username,:sex,:addr)");
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return writer;
    }

    @Bean
    Step excleStep(){
        return stepBuilderFactory.get("excleStep")
                .<User,User>chunk(5000)
                .reader(itemReader())
                .writer(jdbcBatchItemWriter())
                .build();
    }

    @Bean
    Job excelJob(){
        return jobBuilderFactory.get("excelJob")
                .start(excleStep())
                .build();
    }

}

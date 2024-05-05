package com.daeunchung.wmslive.common;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DatabaseCleanser implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager; // @Table 이름으로 전부 받아와서 truncate 시킨다

    private Set<String> tableNames;

    @Override
    public void afterPropertiesSet() {
        tableNames = entityManager.getMetamodel().getEntities().stream()
                .filter(e -> e.getJavaType().isAnnotationPresent(Table.class))
                .map(e -> e.getJavaType().getAnnotation(Table.class).name())
                .collect(Collectors.toUnmodifiableSet());
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate(); // FK 걸린(순차적으로 테이블 삭제해야함) 등 제약조건 해제
        for (final String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName + " RESTART IDENTITY ").executeUpdate();
        }
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }
}

/**
 * API Test 가 개발단계에서 여러개가 생성되면, SpringBootApplication 을 여러번 띄울 수 없기 때문에, 재활용이 된다.
 * auto-ddl : create 로 table drop 이 되는데, seq 는 계속 증가한다
 * 그래서 원래는 findById(seq:1) 로 조회하던 테스트가 seq 에 따라서 테스트 코드가 깨지는 일이 발생할 수 있다
 * 따라서 테스트 @BeforeEach 에서 엔티티 테이블에 내용을 전부 지우고, seq도 초기화하고 실행하도록 한다 -> DatabaseCleanser 추가
 */
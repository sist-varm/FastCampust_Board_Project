package com.fastcampus.board_project.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

// jpa Auditing
@Getter
// Setter를 지정해서 임의로 변경하게 하려고 전체 Setter를 걸지 않는다.
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // mysql은 default의 strategy가 identity. 필요에 따라 변경해주지 않으면 오류 발생
    private Long id;

    @Setter @Column(nullable = false) private String title;   // 제목
    @Setter @Column(nullable = false, length = 10000) private String content; // 본문

    // 위에서 column으로 어노테이션 해주면 일반적으로 column으로 인식
    @Setter private String hashtag; // 해시태그

    // 매핑, 양방향 바인딩
    @ToString.Exclude
    // ToString을 Exclude하지 않으면 순환참조를 끊을 수 없다. (양방향 바인딩의 안좋은점)
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();


    // metadata -> 자동으로 들어가도록 할 예정
    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt;    // 생성일시
    @CreatedBy @Column(nullable = false, length = 100) private String createdBy;           // 생성자
    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt;   // 수정일시
    @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy;          // 수정자

    protected Article() {}

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // 도메인 Article 을 생성할 때는 하단의 값을 필요로 한다고 가이드
    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        // 위의 두줄은 JDK 16이상부터 아래와 같이 대체 가능하다.
        // if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);
        // id != null을 추가하면 새로 만들어져서 영속화되지 않은 엔티티는 동등성 검사를 탈락한다.
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

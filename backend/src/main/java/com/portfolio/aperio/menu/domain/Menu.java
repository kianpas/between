package com.portfolio.aperio.menu.domain;

import com.portfolio.aperio.role.domain.MenuRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "menu")
@ToString(exclude = {"menuRoles"})
@EqualsAndHashCode(of = "menuNo")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuNo;

    private Long upperMenuNo;

    private String menuNm;

    private String menuDsc;

    private String menuUrl;

    @Column(name = "useAt", length = 10)
    private String useAt;

    private LocalDateTime createDt;

    private int sortOrder;

    @Column(name = "menuType", length = 30)
    private String menuType;

    // --- 새로운 @OneToMany 관계 추가 ---
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    // mappedBy: MenuRole 엔티티에 있는 'menu' 필드가 관계의 주인임을 명시
    // cascade: Menu 저장/삭제 시 MenuRole도 함께 처리
    // orphanRemoval: menu.getMenuRoles().remove(menuRole) 시 DB에서도 삭제
    // fetch: LAZY 로딩 권장
    @Builder.Default
    private Set<MenuRole> menuRoles = new HashSet<>();
}

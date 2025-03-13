package com.example.demo.entity;


import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name= "tbl_memo")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder // ✅ 추가
@Getter


public class Memo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mno")
    private Long mno;

    @Column(name = "memo_text", nullable = false) // ✅ 새로운 필드 추가
    private String memoText;

    public void setMemoText(String updateText) {
    }
}
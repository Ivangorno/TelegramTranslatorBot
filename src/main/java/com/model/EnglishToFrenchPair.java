package com.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Component
@Table(name = "ENGLISH_DICTIONARY")
public class EnglishToFrenchPair {

    @Column(name = "english")
    private String englishWord;

    @Column(name = "french")
    private  String frenchWord;
}

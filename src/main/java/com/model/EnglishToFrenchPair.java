package com.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ENGLISH_DICTIONARY")
public class EnglishToFrenchPair {

    @Column(name = "english")
    private String englishWord;

    @Column(name = "french")
    private  String frenchWord;
}

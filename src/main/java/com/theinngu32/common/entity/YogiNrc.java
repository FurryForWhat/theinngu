package com.theinngu32.common.entity;

import com.theinngu32.common.converter.NrcTypeConverter;
import com.theinngu32.common.converter.RatingConverter;
import com.theinngu32.common.entity.constants.NrcType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "yogi_nrcs")
public class YogiNrc extends MasterData{

    @Column
    private String postFixDigit;

    @Column
    @Convert(converter = NrcTypeConverter.class)
    private NrcType type;

    @ManyToOne
    @JoinColumn(name = "nrc_code_id", nullable = false)
    private NrcCode nrcCode;

    @OneToOne
    @JoinColumn(name = "yogi_id", nullable = false)
    private Yogi yogi;

//    public String getMmNrc(){
//        return
//    }
//
//    public String getEnNrc(){
//        return
//    }
}

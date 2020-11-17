package com.easycar.spring.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CustomerReturn")
public class Return {
    @Id
    String rid;
    String dteOfReturn;
    double damages;

    @OneToOne()
    @JoinColumn(name = "bd_col",referencedColumnName = "detailId")
    private  BookingDetail bookingDetail;

    @OneToOne(mappedBy = "_return",cascade = CascadeType.ALL)
    private Payment payment;
}

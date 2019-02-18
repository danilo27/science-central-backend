package com.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.model.enums.MagazinePaymentType;
 
@Entity
@Table(name = "MAGAZINE")
public class Magazine implements Serializable{

    @Id
    @Column(name = "ISSN", length = 8)
    private String issn;

    @Column(name = "NAME", length = 120, nullable = false)
    private String name;
    
    //da li se naplaćuje čitaocima ili autorima (OPEN ACCESS / PAID ACCESS)
    @Column(name = "PAYMENT_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private MagazinePaymentType paymentType;

    @Column(name = "MEMBERSHIP_PRICE", nullable = true)
    private Double membershipPrice;

    @OneToOne(optional = false)
    @JoinColumn(name = "CHIEF_EDITOR", unique = true)
    private Editor editor;

    /**
     * JoinTable -> Pravi se posebna (intermediary) tabela - MagazineFields
     *  
     * JoinColumn -> The name attribute contains the column name of the intermediary table (MAGAZINE)
     * 
     * 
     *  name: This is the name of third table. 
		joinColumns: Assign the column of third table related to entity itself. 
		inverseJoinColumns: Assign the column of third table related to associated entity. 
     * 
     * 
     *      
     *  Naucna oblast moze da bude vezana za vise casopisa i obrnuto -> pa prema tome ManyToMany kojom se pravi posebna tabela uvek
     *   
     */
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "MAGAZINE_FIELDS", 
    joinColumns = @JoinColumn(name = "MAGAZINE"), 
    inverseJoinColumns = @JoinColumn(name = "FIELD"))
    private Set<FieldOfScience> fields = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "MAGAZINE_PAYMENT_OPTIONS", 
    joinColumns = @JoinColumn(name = "MAGAZINE"),
    inverseJoinColumns = @JoinColumn(name = "PAYMENT_OPTION"))
    private Set<PaymentOption> options = new HashSet<>();
    
    
    
    public Magazine(){}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MagazinePaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(MagazinePaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public Double getMembershipPrice() {
		return membershipPrice;
	}

	public void setMembershipPrice(Double membershipPrice) {
		this.membershipPrice = membershipPrice;
	}

	public Editor getEditor() {
		return editor;
	}

	public void setEditor(Editor editor) {
		this.editor = editor;
	}

	public Set<FieldOfScience> getFields() {
		return fields;
	}

	public void setFields(Set<FieldOfScience> fields) {
		this.fields = fields;
	}

	public Set<PaymentOption> getOptions() {
		return options;
	}

	public void setOptions(Set<PaymentOption> options) {
		this.options = options;
	}

	public Magazine(String issn, String name, MagazinePaymentType paymentType, Double membershipPrice, Editor editor,
			Set<FieldOfScience> fields, Set<PaymentOption> options) {
		super();
		this.issn = issn;
		this.name = name;
		this.paymentType = paymentType;
		this.membershipPrice = membershipPrice;
		this.editor = editor;
		this.fields = fields;
		this.options = options;
	}

	@Override
	public String toString() {
		return "Magazine [issn=" + issn + ", name=" + name + ", paymentType=" + paymentType + ", membershipPrice="
				+ membershipPrice + ", editor=" + editor + ", fields=" + fields + ", options=" + options + "]";
	}
    
    
}

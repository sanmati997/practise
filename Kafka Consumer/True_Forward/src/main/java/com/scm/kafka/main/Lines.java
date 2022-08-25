package com.scm.kafka.main;

import java.math.BigDecimal;
import java.sql.Date;

//import java.sql.Date;

public class Lines 
{

//	Date trueForwardDate;
//	@JsonProperty("instanceId")
//	Long Instance_Id;
//	@JsonProperty("cplId")
//	Long  CPL_Id;
//	Long installSiteId;
//	Long webOrderLineID;
//	double originalListPrice;
//	Long defaultTerm;
//	Long eaFactor;
//	double unitListPrice;
//	double serviceListPrice;somulani@
//	double protectedDiscount;
//	double unitNetPriceBeforeCredit;
//	double extendedNetPriceBeforeCredit;
//	double creditAmount;
//	String creditCode;
//	double serviceNetPrice;
//	double monthlyCredit;
//	double monthlyEAListPrice;
//	double residualMonthlyListPrice;
//	double monthlyEACredit;
//	double residualMonthlyCredit;
	
	private Date true_forward_date;
	public Date getTrue_forward_date() {
		return true_forward_date;
	}
	public void setTrue_forward_date(Date true_forward_date) {
		this.true_forward_date = true_forward_date;
	}
	public long getInstance_id() {
		return instance_id;
	}
	public void setInstance_id(long instance_id) {
		this.instance_id = instance_id;
	}
	public long getCpl_id() {
		return cpl_id;
	}
	public void setCpl_id(long cpl_id) {
		this.cpl_id = cpl_id;
	}
	public long getInstall_site_loc() {
		return install_site_loc;
	}
	public void setInstall_site_loc(long install_site_loc) {
		this.install_site_loc = install_site_loc;
	}
	public long getWeb_ord_id() {
		return web_ord_id;
	}
	public void setWeb_ord_id(long web_ord_id) {
		this.web_ord_id = web_ord_id;
	}
	public long getWeb_order_line_id() {
		return web_order_line_id;
	}
	public void setWeb_order_line_id(long web_order_line_id) {
		this.web_order_line_id = web_order_line_id;
	}
	public BigDecimal getOriginal_list_price() {
		return original_list_price;
	}
	public void setOriginal_list_price(BigDecimal original_list_price) {
		this.original_list_price = original_list_price;
	}
	public long getDefault_term() {
		return default_term;
	}
	public void setDefault_term(long default_term) {
		this.default_term = default_term;
	}
	public BigDecimal getEa_Factor() {
		return ea_Factor;
	}
	public void setEa_Factor(BigDecimal ea_Factor) {
		this.ea_Factor = ea_Factor;
	}
	public BigDecimal getUnit_list_price() {
		return unit_list_price;
	}
	public void setUnit_list_price(BigDecimal unit_list_price) {
		this.unit_list_price = unit_list_price;
	}
	public BigDecimal getService_list_price() {
		return service_list_price;
	}
	public void setService_list_price(BigDecimal service_list_price) {
		this.service_list_price = service_list_price;
	}
	public BigDecimal getProtected_discount() {
		return protected_discount;
	}
	public void setProtected_discount(BigDecimal protected_discount) {
		this.protected_discount = protected_discount;
	}
	public BigDecimal getUnit_net_price_before_credit() {
		return unit_net_price_before_credit;
	}
	public void setUnit_net_price_before_credit(BigDecimal unit_net_price_before_credit) {
		this.unit_net_price_before_credit = unit_net_price_before_credit;
	}
	public BigDecimal getExtended_net_price_bfr_credit() {
		return extended_net_price_bfr_credit;
	}
	public void setExtended_net_price_bfr_credit(BigDecimal extended_net_price_bfr_credit) {
		this.extended_net_price_bfr_credit = extended_net_price_bfr_credit;
	}
	public BigDecimal getCredit_amount() {
		return credit_amount;
	}
	public void setCredit_amount(BigDecimal credit_amount) {
		this.credit_amount = credit_amount;
	}
	public String getCredit_code() {
		return credit_code;
	}
	public void setCredit_code(String credit_code) {
		this.credit_code = credit_code;
	}
	public BigDecimal getService_net_price() {
		return service_net_price;
	}
	public void setService_net_price(BigDecimal service_net_price) {
		this.service_net_price = service_net_price;
	}
	public BigDecimal getMonthly_credit() {
		return monthly_credit;
	}
	public void setMonthly_credit(BigDecimal monthly_credit) {
		this.monthly_credit = monthly_credit;
	}
	public BigDecimal getMonthly_ea_list_price() {
		return monthly_ea_list_price;
	}
	public void setMonthly_ea_list_price(BigDecimal monthly_ea_list_price) {
		this.monthly_ea_list_price = monthly_ea_list_price;
	}
	public BigDecimal getResidual_monthly_list_price() {
		return residual_monthly_list_price;
	}
	public void setResidual_monthly_list_price(BigDecimal residual_monthly_list_price) {
		this.residual_monthly_list_price = residual_monthly_list_price;
	}
	public BigDecimal getMonthly_ea_credit() {
		return monthly_ea_credit;
	}
	public void setMonthly_ea_credit(BigDecimal monthly_ea_credit) {
		this.monthly_ea_credit = monthly_ea_credit;
	}
	public BigDecimal getResidual_monthly_credit() {
		return residual_monthly_credit;
	}
	public void setResidual_monthly_credit(BigDecimal residual_monthly_credit) {
		this.residual_monthly_credit = residual_monthly_credit;
	}
	public BigDecimal getCcwr_transaction_id() {
		return ccwr_transaction_id;
	}
	public void setCcwr_transaction_id(BigDecimal ccwr_transaction_id) {
		this.ccwr_transaction_id = ccwr_transaction_id;
	}
	private long instance_id;
	private long cpl_id;
	private long install_site_loc;
	private long web_ord_id;
	private long web_order_line_id;
	private BigDecimal original_list_price;
	private long default_term;
	private BigDecimal ea_Factor;
	private BigDecimal unit_list_price;
	private BigDecimal service_list_price;
	private BigDecimal protected_discount;
	private BigDecimal unit_net_price_before_credit;
	private BigDecimal extended_net_price_bfr_credit;
	private BigDecimal credit_amount;
	private String credit_code;
	private BigDecimal service_net_price;
	private BigDecimal monthly_credit;
	private BigDecimal monthly_ea_list_price;
	private BigDecimal residual_monthly_list_price;
	private BigDecimal monthly_ea_credit;
	private BigDecimal residual_monthly_credit;
	private BigDecimal ccwr_transaction_id;
	
	
}

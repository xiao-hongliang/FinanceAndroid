package com.pudding.financeandroid.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 主页面的接口定义bean
 *
 * Created by xiao.hongliang on 2016/8/27.
 */
public class MainBean implements Serializable{
    private static final long serialVersionUID = 1L;

    private List<FinancingBean> financeList;
    private List<LoanBean> loanList;
    private List<AdvsListBean> advsList;

    public List<FinancingBean> getFinanceList() {
        return financeList;
    }

    public void setFinanceList(List<FinancingBean> financeList) {
        this.financeList = financeList;
    }

    public List<LoanBean> getLoanList() {
        return loanList;
    }

    public void setLoanList(List<LoanBean> loanList) {
        this.loanList = loanList;
    }

    public List<AdvsListBean> getAdvsList() {
        return advsList;
    }

    public void setAdvsList(List<AdvsListBean> advsList) {
        this.advsList = advsList;
    }
}

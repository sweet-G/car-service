package com.zt.erp.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CountDaliyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public CountDaliyExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSumNumIsNull() {
            addCriterion("sum_num is null");
            return (Criteria) this;
        }

        public Criteria andSumNumIsNotNull() {
            addCriterion("sum_num is not null");
            return (Criteria) this;
        }

        public Criteria andSumNumEqualTo(Integer value) {
            addCriterion("sum_num =", value, "sumNum");
            return (Criteria) this;
        }

        public Criteria andSumNumNotEqualTo(Integer value) {
            addCriterion("sum_num <>", value, "sumNum");
            return (Criteria) this;
        }

        public Criteria andSumNumGreaterThan(Integer value) {
            addCriterion("sum_num >", value, "sumNum");
            return (Criteria) this;
        }

        public Criteria andSumNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("sum_num >=", value, "sumNum");
            return (Criteria) this;
        }

        public Criteria andSumNumLessThan(Integer value) {
            addCriterion("sum_num <", value, "sumNum");
            return (Criteria) this;
        }

        public Criteria andSumNumLessThanOrEqualTo(Integer value) {
            addCriterion("sum_num <=", value, "sumNum");
            return (Criteria) this;
        }

        public Criteria andSumNumIn(List<Integer> values) {
            addCriterion("sum_num in", values, "sumNum");
            return (Criteria) this;
        }

        public Criteria andSumNumNotIn(List<Integer> values) {
            addCriterion("sum_num not in", values, "sumNum");
            return (Criteria) this;
        }

        public Criteria andSumNumBetween(Integer value1, Integer value2) {
            addCriterion("sum_num between", value1, value2, "sumNum");
            return (Criteria) this;
        }

        public Criteria andSumNumNotBetween(Integer value1, Integer value2) {
            addCriterion("sum_num not between", value1, value2, "sumNum");
            return (Criteria) this;
        }

        public Criteria andSumMoneyIsNull() {
            addCriterion("sum_money is null");
            return (Criteria) this;
        }

        public Criteria andSumMoneyIsNotNull() {
            addCriterion("sum_money is not null");
            return (Criteria) this;
        }

        public Criteria andSumMoneyEqualTo(BigDecimal value) {
            addCriterion("sum_money =", value, "sumMoney");
            return (Criteria) this;
        }

        public Criteria andSumMoneyNotEqualTo(BigDecimal value) {
            addCriterion("sum_money <>", value, "sumMoney");
            return (Criteria) this;
        }

        public Criteria andSumMoneyGreaterThan(BigDecimal value) {
            addCriterion("sum_money >", value, "sumMoney");
            return (Criteria) this;
        }

        public Criteria andSumMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("sum_money >=", value, "sumMoney");
            return (Criteria) this;
        }

        public Criteria andSumMoneyLessThan(BigDecimal value) {
            addCriterion("sum_money <", value, "sumMoney");
            return (Criteria) this;
        }

        public Criteria andSumMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("sum_money <=", value, "sumMoney");
            return (Criteria) this;
        }

        public Criteria andSumMoneyIn(List<BigDecimal> values) {
            addCriterion("sum_money in", values, "sumMoney");
            return (Criteria) this;
        }

        public Criteria andSumMoneyNotIn(List<BigDecimal> values) {
            addCriterion("sum_money not in", values, "sumMoney");
            return (Criteria) this;
        }

        public Criteria andSumMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sum_money between", value1, value2, "sumMoney");
            return (Criteria) this;
        }

        public Criteria andSumMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sum_money not between", value1, value2, "sumMoney");
            return (Criteria) this;
        }

        public Criteria andDatatimeIsNull() {
            addCriterion("datatime is null");
            return (Criteria) this;
        }

        public Criteria andDatatimeIsNotNull() {
            addCriterion("datatime is not null");
            return (Criteria) this;
        }

        public Criteria andDatatimeEqualTo(String value) {
            addCriterion("datatime =", value, "datatime");
            return (Criteria) this;
        }

        public Criteria andDatatimeNotEqualTo(String value) {
            addCriterion("datatime <>", value, "datatime");
            return (Criteria) this;
        }

        public Criteria andDatatimeGreaterThan(String value) {
            addCriterion("datatime >", value, "datatime");
            return (Criteria) this;
        }

        public Criteria andDatatimeGreaterThanOrEqualTo(String value) {
            addCriterion("datatime >=", value, "datatime");
            return (Criteria) this;
        }

        public Criteria andDatatimeLessThan(String value) {
            addCriterion("datatime <", value, "datatime");
            return (Criteria) this;
        }

        public Criteria andDatatimeLessThanOrEqualTo(String value) {
            addCriterion("datatime <=", value, "datatime");
            return (Criteria) this;
        }

        public Criteria andDatatimeLike(String value) {
            addCriterion("datatime like", value, "datatime");
            return (Criteria) this;
        }

        public Criteria andDatatimeNotLike(String value) {
            addCriterion("datatime not like", value, "datatime");
            return (Criteria) this;
        }

        public Criteria andDatatimeIn(List<String> values) {
            addCriterion("datatime in", values, "datatime");
            return (Criteria) this;
        }

        public Criteria andDatatimeNotIn(List<String> values) {
            addCriterion("datatime not in", values, "datatime");
            return (Criteria) this;
        }

        public Criteria andDatatimeBetween(String value1, String value2) {
            addCriterion("datatime between", value1, value2, "datatime");
            return (Criteria) this;
        }

        public Criteria andDatatimeNotBetween(String value1, String value2) {
            addCriterion("datatime not between", value1, value2, "datatime");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
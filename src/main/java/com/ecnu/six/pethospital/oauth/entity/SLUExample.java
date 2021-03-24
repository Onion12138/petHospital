package com.ecnu.six.pethospital.oauth.entity;

import java.util.ArrayList;
import java.util.List;

public class SLUExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SLUExample() {
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

        public Criteria andLocalUIdIsNull() {
            addCriterion("local_u_id is null");
            return (Criteria) this;
        }

        public Criteria andLocalUIdIsNotNull() {
            addCriterion("local_u_id is not null");
            return (Criteria) this;
        }

        public Criteria andLocalUIdEqualTo(Integer value) {
            addCriterion("local_u_id =", value, "localUId");
            return (Criteria) this;
        }

        public Criteria andLocalUIdNotEqualTo(Integer value) {
            addCriterion("local_u_id <>", value, "localUId");
            return (Criteria) this;
        }

        public Criteria andLocalUIdGreaterThan(Integer value) {
            addCriterion("local_u_id >", value, "localUId");
            return (Criteria) this;
        }

        public Criteria andLocalUIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("local_u_id >=", value, "localUId");
            return (Criteria) this;
        }

        public Criteria andLocalUIdLessThan(Integer value) {
            addCriterion("local_u_id <", value, "localUId");
            return (Criteria) this;
        }

        public Criteria andLocalUIdLessThanOrEqualTo(Integer value) {
            addCriterion("local_u_id <=", value, "localUId");
            return (Criteria) this;
        }

        public Criteria andLocalUIdIn(List<Integer> values) {
            addCriterion("local_u_id in", values, "localUId");
            return (Criteria) this;
        }

        public Criteria andLocalUIdNotIn(List<Integer> values) {
            addCriterion("local_u_id not in", values, "localUId");
            return (Criteria) this;
        }

        public Criteria andLocalUIdBetween(Integer value1, Integer value2) {
            addCriterion("local_u_id between", value1, value2, "localUId");
            return (Criteria) this;
        }

        public Criteria andLocalUIdNotBetween(Integer value1, Integer value2) {
            addCriterion("local_u_id not between", value1, value2, "localUId");
            return (Criteria) this;
        }

        public Criteria andSocialUIdIsNull() {
            addCriterion("social_u_id is null");
            return (Criteria) this;
        }

        public Criteria andSocialUIdIsNotNull() {
            addCriterion("social_u_id is not null");
            return (Criteria) this;
        }

        public Criteria andSocialUIdEqualTo(Integer value) {
            addCriterion("social_u_id =", value, "socialUId");
            return (Criteria) this;
        }

        public Criteria andSocialUIdNotEqualTo(Integer value) {
            addCriterion("social_u_id <>", value, "socialUId");
            return (Criteria) this;
        }

        public Criteria andSocialUIdGreaterThan(Integer value) {
            addCriterion("social_u_id >", value, "socialUId");
            return (Criteria) this;
        }

        public Criteria andSocialUIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("social_u_id >=", value, "socialUId");
            return (Criteria) this;
        }

        public Criteria andSocialUIdLessThan(Integer value) {
            addCriterion("social_u_id <", value, "socialUId");
            return (Criteria) this;
        }

        public Criteria andSocialUIdLessThanOrEqualTo(Integer value) {
            addCriterion("social_u_id <=", value, "socialUId");
            return (Criteria) this;
        }

        public Criteria andSocialUIdIn(List<Integer> values) {
            addCriterion("social_u_id in", values, "socialUId");
            return (Criteria) this;
        }

        public Criteria andSocialUIdNotIn(List<Integer> values) {
            addCriterion("social_u_id not in", values, "socialUId");
            return (Criteria) this;
        }

        public Criteria andSocialUIdBetween(Integer value1, Integer value2) {
            addCriterion("social_u_id between", value1, value2, "socialUId");
            return (Criteria) this;
        }

        public Criteria andSocialUIdNotBetween(Integer value1, Integer value2) {
            addCriterion("social_u_id not between", value1, value2, "socialUId");
            return (Criteria) this;
        }
    }

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
package cn.pledge.envconsole.book.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserExample() {
        oredCriteria = new ArrayList<>();
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
            criteria = new ArrayList<>();
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

        public Criteria andUserAddressIsNull() {
            addCriterion("user_address is null");
            return (Criteria) this;
        }

        public Criteria andUserAddressIsNotNull() {
            addCriterion("user_address is not null");
            return (Criteria) this;
        }

        public Criteria andUserAddressEqualTo(String value) {
            addCriterion("user_address =", value, "userAddress");
            return (Criteria) this;
        }

        public Criteria andUserAddressNotEqualTo(String value) {
            addCriterion("user_address <>", value, "userAddress");
            return (Criteria) this;
        }

        public Criteria andUserAddressGreaterThan(String value) {
            addCriterion("user_address >", value, "userAddress");
            return (Criteria) this;
        }

        public Criteria andUserAddressGreaterThanOrEqualTo(String value) {
            addCriterion("user_address >=", value, "userAddress");
            return (Criteria) this;
        }

        public Criteria andUserAddressLessThan(String value) {
            addCriterion("user_address <", value, "userAddress");
            return (Criteria) this;
        }

        public Criteria andUserAddressLessThanOrEqualTo(String value) {
            addCriterion("user_address <=", value, "userAddress");
            return (Criteria) this;
        }

        public Criteria andUserAddressLike(String value) {
            addCriterion("user_address like", value, "userAddress");
            return (Criteria) this;
        }

        public Criteria andUserAddressNotLike(String value) {
            addCriterion("user_address not like", value, "userAddress");
            return (Criteria) this;
        }

        public Criteria andUserAddressIn(List<String> values) {
            addCriterion("user_address in", values, "userAddress");
            return (Criteria) this;
        }

        public Criteria andUserAddressNotIn(List<String> values) {
            addCriterion("user_address not in", values, "userAddress");
            return (Criteria) this;
        }

        public Criteria andUserAddressBetween(String value1, String value2) {
            addCriterion("user_address between", value1, value2, "userAddress");
            return (Criteria) this;
        }

        public Criteria andUserAddressNotBetween(String value1, String value2) {
            addCriterion("user_address not between", value1, value2, "userAddress");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(LocalDate value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(LocalDate value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(LocalDate value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(LocalDate value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(LocalDate value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(LocalDate value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<LocalDate> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<LocalDate> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(LocalDate value1, LocalDate value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(LocalDate value1, LocalDate value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andSuperiorUserAddressIsNull() {
            addCriterion("superior_user_address is null");
            return (Criteria) this;
        }

        public Criteria andSuperiorUserAddressIsNotNull() {
            addCriterion("superior_user_address is not null");
            return (Criteria) this;
        }

        public Criteria andSuperiorUserAddressEqualTo(String value) {
            addCriterion("superior_user_address =", value, "superiorUserAddress");
            return (Criteria) this;
        }

        public Criteria andSuperiorUserAddressNotEqualTo(String value) {
            addCriterion("superior_user_address <>", value, "superiorUserAddress");
            return (Criteria) this;
        }

        public Criteria andSuperiorUserAddressGreaterThan(String value) {
            addCriterion("superior_user_address >", value, "superiorUserAddress");
            return (Criteria) this;
        }

        public Criteria andSuperiorUserAddressGreaterThanOrEqualTo(String value) {
            addCriterion("superior_user_address >=", value, "superiorUserAddress");
            return (Criteria) this;
        }

        public Criteria andSuperiorUserAddressLessThan(String value) {
            addCriterion("superior_user_address <", value, "superiorUserAddress");
            return (Criteria) this;
        }

        public Criteria andSuperiorUserAddressLessThanOrEqualTo(String value) {
            addCriterion("superior_user_address <=", value, "superiorUserAddress");
            return (Criteria) this;
        }

        public Criteria andSuperiorUserAddressLike(String value) {
            addCriterion("superior_user_address like", value, "superiorUserAddress");
            return (Criteria) this;
        }

        public Criteria andSuperiorUserAddressNotLike(String value) {
            addCriterion("superior_user_address not like", value, "superiorUserAddress");
            return (Criteria) this;
        }

        public Criteria andSuperiorUserAddressIn(List<String> values) {
            addCriterion("superior_user_address in", values, "superiorUserAddress");
            return (Criteria) this;
        }

        public Criteria andSuperiorUserAddressNotIn(List<String> values) {
            addCriterion("superior_user_address not in", values, "superiorUserAddress");
            return (Criteria) this;
        }

        public Criteria andSuperiorUserAddressBetween(String value1, String value2) {
            addCriterion("superior_user_address between", value1, value2, "superiorUserAddress");
            return (Criteria) this;
        }

        public Criteria andSuperiorUserAddressNotBetween(String value1, String value2) {
            addCriterion("superior_user_address not between", value1, value2, "superiorUserAddress");
            return (Criteria) this;
        }

        public Criteria andIsFlowRewardIsNull() {
            addCriterion("is_flow_reward is null");
            return (Criteria) this;
        }

        public Criteria andIsFlowRewardIsNotNull() {
            addCriterion("is_flow_reward is not null");
            return (Criteria) this;
        }

        public Criteria andIsFlowRewardEqualTo(Boolean value) {
            addCriterion("is_flow_reward =", value, "isFlowReward");
            return (Criteria) this;
        }

        public Criteria andIsFlowRewardNotEqualTo(Boolean value) {
            addCriterion("is_flow_reward <>", value, "isFlowReward");
            return (Criteria) this;
        }

        public Criteria andIsFlowRewardGreaterThan(Boolean value) {
            addCriterion("is_flow_reward >", value, "isFlowReward");
            return (Criteria) this;
        }

        public Criteria andIsFlowRewardGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_flow_reward >=", value, "isFlowReward");
            return (Criteria) this;
        }

        public Criteria andIsFlowRewardLessThan(Boolean value) {
            addCriterion("is_flow_reward <", value, "isFlowReward");
            return (Criteria) this;
        }

        public Criteria andIsFlowRewardLessThanOrEqualTo(Boolean value) {
            addCriterion("is_flow_reward <=", value, "isFlowReward");
            return (Criteria) this;
        }

        public Criteria andIsFlowRewardIn(List<Boolean> values) {
            addCriterion("is_flow_reward in", values, "isFlowReward");
            return (Criteria) this;
        }

        public Criteria andIsFlowRewardNotIn(List<Boolean> values) {
            addCriterion("is_flow_reward not in", values, "isFlowReward");
            return (Criteria) this;
        }

        public Criteria andIsFlowRewardBetween(Boolean value1, Boolean value2) {
            addCriterion("is_flow_reward between", value1, value2, "isFlowReward");
            return (Criteria) this;
        }

        public Criteria andIsFlowRewardNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_flow_reward not between", value1, value2, "isFlowReward");
            return (Criteria) this;
        }

        public Criteria andIsWithdrawalAuthIsNull() {
            addCriterion("is_withdrawal_auth is null");
            return (Criteria) this;
        }

        public Criteria andIsWithdrawalAuthIsNotNull() {
            addCriterion("is_withdrawal_auth is not null");
            return (Criteria) this;
        }

        public Criteria andIsWithdrawalAuthEqualTo(Boolean value) {
            addCriterion("is_withdrawal_auth =", value, "isWithdrawalAuth");
            return (Criteria) this;
        }

        public Criteria andIsWithdrawalAuthNotEqualTo(Boolean value) {
            addCriterion("is_withdrawal_auth <>", value, "isWithdrawalAuth");
            return (Criteria) this;
        }

        public Criteria andIsWithdrawalAuthGreaterThan(Boolean value) {
            addCriterion("is_withdrawal_auth >", value, "isWithdrawalAuth");
            return (Criteria) this;
        }

        public Criteria andIsWithdrawalAuthGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_withdrawal_auth >=", value, "isWithdrawalAuth");
            return (Criteria) this;
        }

        public Criteria andIsWithdrawalAuthLessThan(Boolean value) {
            addCriterion("is_withdrawal_auth <", value, "isWithdrawalAuth");
            return (Criteria) this;
        }

        public Criteria andIsWithdrawalAuthLessThanOrEqualTo(Boolean value) {
            addCriterion("is_withdrawal_auth <=", value, "isWithdrawalAuth");
            return (Criteria) this;
        }

        public Criteria andIsWithdrawalAuthIn(List<Boolean> values) {
            addCriterion("is_withdrawal_auth in", values, "isWithdrawalAuth");
            return (Criteria) this;
        }

        public Criteria andIsWithdrawalAuthNotIn(List<Boolean> values) {
            addCriterion("is_withdrawal_auth not in", values, "isWithdrawalAuth");
            return (Criteria) this;
        }

        public Criteria andIsWithdrawalAuthBetween(Boolean value1, Boolean value2) {
            addCriterion("is_withdrawal_auth between", value1, value2, "isWithdrawalAuth");
            return (Criteria) this;
        }

        public Criteria andIsWithdrawalAuthNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_withdrawal_auth not between", value1, value2, "isWithdrawalAuth");
            return (Criteria) this;
        }

        public Criteria andSuperiorIdIsNull() {
            addCriterion("superior_id is null");
            return (Criteria) this;
        }

        public Criteria andSuperiorIdIsNotNull() {
            addCriterion("superior_id is not null");
            return (Criteria) this;
        }

        public Criteria andSuperiorIdEqualTo(Integer value) {
            addCriterion("superior_id =", value, "superiorId");
            return (Criteria) this;
        }

        public Criteria andSuperiorIdNotEqualTo(Integer value) {
            addCriterion("superior_id <>", value, "superiorId");
            return (Criteria) this;
        }

        public Criteria andSuperiorIdGreaterThan(Integer value) {
            addCriterion("superior_id >", value, "superiorId");
            return (Criteria) this;
        }

        public Criteria andSuperiorIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("superior_id >=", value, "superiorId");
            return (Criteria) this;
        }

        public Criteria andSuperiorIdLessThan(Integer value) {
            addCriterion("superior_id <", value, "superiorId");
            return (Criteria) this;
        }

        public Criteria andSuperiorIdLessThanOrEqualTo(Integer value) {
            addCriterion("superior_id <=", value, "superiorId");
            return (Criteria) this;
        }

        public Criteria andSuperiorIdIn(List<Integer> values) {
            addCriterion("superior_id in", values, "superiorId");
            return (Criteria) this;
        }

        public Criteria andSuperiorIdNotIn(List<Integer> values) {
            addCriterion("superior_id not in", values, "superiorId");
            return (Criteria) this;
        }

        public Criteria andSuperiorIdBetween(Integer value1, Integer value2) {
            addCriterion("superior_id between", value1, value2, "superiorId");
            return (Criteria) this;
        }

        public Criteria andSuperiorIdNotBetween(Integer value1, Integer value2) {
            addCriterion("superior_id not between", value1, value2, "superiorId");
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
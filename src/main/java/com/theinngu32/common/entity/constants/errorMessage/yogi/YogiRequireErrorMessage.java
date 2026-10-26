package com.theinngu32.common.entity.constants.errorMessage.yogi;

import com.theinngu32.common.entity.constants.errorMessage.ErrorMessageBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum YogiRequireErrorMessage implements ErrorMessageBaseEnum<String> {
    NAME("name", "အမည် မဖြည့်ထားပါ။"),
    FATHER_NAME("fatherName", "အဖအမည် မဖြည့်ထားပါ။"),
    PHONE("phone", "ဖုန်းနံပါတ် မဖြည့်ထားပါ။"),
    GENDER_TYPE("genderType", "ယောဂီအမျိုးအစား မဖြည့်ထားပါ။"),
    BIRTH_DATE("birthDate", "မွေးနေ့ မဖြည့်ထားပါ။"),
    MONK_DATE("monkDate", "ရဟန်းဝတ်သောခုနှစ် မဖြည့်ထားပါ။"),
    CITY("city", "နေထိုင်သည့်မြို့ မဖြည့်ထားပါ။"),
    REGION("region", "နေထိုင်သည့်တိုင်းဒေသကြီး မဖြည့်ထားပါ။"),
    ADDRESS("address", "နေရပ်လိပ်စာ မဖြည့်ထားပါ။"),
    EMERGENCY_CONTACT("emergencyContact", "အရေးပေါ် အဆက်အသွယ် မဖြည့်ထားပါ။"),
    LEVEL("level", "ယောဂီအဆင့် မဖြည့်ထားပါ။"),
    SUB_MISSIONARY_GROUP("subMissionaryGroup", "သာသနာပြုအဖွဲ့ မဖြည့်ထားပါ။"),
    MISSIONARY_GROUP("MissionaryGroup", "ဌာနချုပ်ရိပ်သာ မဖြည့်ထားပါ။");

    private final String value;
    private final String errorMessage;

}
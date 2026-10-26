package com.theinngu32.common.entity.constants.errorMessage.tempYogi;

import com.theinngu32.common.entity.constants.errorMessage.ErrorMessageBaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TempYogiRequireErrorMessage implements ErrorMessageBaseEnum<String> {
    NAME("name", "ဘွဲ့/အမည် မဖြည့်ထားပါ။"),
    FATHER_NAME("fatherName", "အဖအမည် မဖြည့်ထားပါ။"),
    YOGI_ID("yogiId","ယောဂီမှတ်ပုံတင်စာအုပ်အမှတ် မဖြည့်ထားပါ။"),
    LEVEL("level","ယောဂီအဆင့် မဖြည့်ထားပါ။"),
    PHONE("phone","ဖုန်းနံပါတ် မဖြည့်ထားပါ။"),
    GENDER("gender","ယောဂီ အမျိုးအစား မဖြည့်ထားပါ။"),
    EDUCATION("education","ပညာအရည်အချင်း/ဘွဲ့တော် မဖြည့်ထားပါ။"),
    REGION("region","နေထိုင်သည့်ပြည်နယ်/တိုင်းဒေသကြီး မဖြည့်ထားပါ။"),
    CITY("city","နေထိုင်သည့် မြို့ မဖြည့်ထားပါ။"),
    ADDRESS("address","နေရပ်လိပ်စာ မဖြည့်ထားပါ။"),
    OTHER_MEDITATION_CENTER("otherMedicationCenter","ဝင်ရောက်ခဲ့ဖူးသော အခြားသော တရားစခန်းများ မဖြည့်ထားပါ။"),
    HEALTH_STATUS("healthStatus","ကျန်းမာရေး အခြေအနေ မဖြည့်ထားပါ။"),
    FOOD_NOT_EATEN("foodNotEaten","ရှောင်သော အစားအစာများ မဖြည့်ထားပါ။"),
    EMERGENCY_CONTACT("emergencyContact","အရေးပေါ်ဆက်သွယ်ရမည့် ဖုန်းနံပါတ် မဖြည့်ထားပါ။"),
    STATUS("status","Status မဖြည့်ထားပါ။"),
    REMARK("remark","မှတ်ချက် မဖြည့်ထားပါ။"),
    NRC("nrc","မှတ်ပုံတင်အမှတ် မဖြည့်ထားပါ။"),
    DOB("birthDate","မွေးသက္ကရာဇ် မဖြည့်ထားပါ။"),
    MONK_DATE("monkDate","ရဟန်းဖြစ်သောနေ့ရက် မဖြည့်ထားပါ။");

    private final String value;
    private final String errorMessage;
}


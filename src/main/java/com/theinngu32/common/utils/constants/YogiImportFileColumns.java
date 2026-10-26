package com.theinngu32.common.utils.constants;

import com.theinngu32.common.entity.constants.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum YogiImportFileColumns implements BaseEnum<String> {
    NAME("ဘွဲ့ / အမည်"),
    FATHER_NAME("အဖမည်"),
    YOGI_ID("ယောဂီမှတ်ပုံတင်စာအုပ်အမှတ်"),
    LEVEL("ယောဂီ Level"),
    PASSPORT("ID / Passport"),
    PHONE("ဖုန်းနံပါတ်"),
    GENDER("ယောဂီ အမျိုးအစား"),
    EDUCATION("ပညာရည်ချင်း / ဘွဲ့တော်"),
    REGION("နေထိုင်သည့်ပြည်နယ်/တိုင်းဒေသကြီး"),
    COUNTRY("နေထိုင်သည့်နိုင်ငံ"),
    CITY("နေထိုင်သည့် မြို့"),
    ADDRESS("နေရပ်လိပ်စာ"),
    OTHER_MEDITATION_CENTER("ဝင်ခဲ့ဖူးသော အခြားတရားစခန်းများ"),
    HEALTH_STATUS("ကျန်းမာရေး အခြေအနေ"),
    FOOD_NOT_EATEN("ရှောင်သော အစားအစာများ"),
    EMERGENCY_CONTACT("အရေးပေါ်ဆက်သွယ်ရန်ဖုန်းနံပါတ်"),
    STATUS("Status"),
    REMARK("မှတ်ချက်"),
    NRC("မှတ်ပုံတင်အမှတ်"),
    DOB("မွေးသက္ကရာဇ်"),
    MONK_DATE("ရဟန်းဖြစ်သောနေ့စွဲ");

    private final String value;

    @Override
    public String getValue() {
        return this.value;
    }

    public static YogiImportFileColumns fromValue(String value) {
        for (YogiImportFileColumns column : YogiImportFileColumns.values()) {
            if (column.getValue().equals(value)) {
                return column;
            }
        }
        throw new IllegalArgumentException("No enum constant found for value: " + value);
    }
}

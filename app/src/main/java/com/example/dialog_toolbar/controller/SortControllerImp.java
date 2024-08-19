package com.example.dialog_toolbar.controller;

import android.util.Log;

import com.example.dialog_toolbar.model.Student;

import java.text.RuleBasedCollator;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SortControllerImp implements SortController {
    @Override
    public void sortByNameASC(List<Student> students) {
        // sắp xếp tên tiếng việt có dấu.
        // lưu ý cho kí tự & đầu chuỗi quy tắc so sánh.
        // trong java thuần ta thay & bằng <
        String rules = "&a<á<à<ả<ã<ạ<ă<ắ<ằ<ẳ<ẵ<ặ<â<ấ<ầ<ẩ<ẫ<ậ" +
                "<b<c<d<đ<e<é<è<ẻ<ẽ<ẹ<ê<ế<ề<ể<ễ<ệ<f<g<h" +
                "<i<í<ì<ỉ<ĩ<ị<j<k<l<m<n<o<ó<ò<ỏ<õ<ọ<ô<ố<ồ<ỗ" +
                "<ộ<ơ<ớ<ờ<ở<ỡ<ợ<p<q<r<s<t" +
                "<u<ú<ù<ủ<ũ<ụ<ư<ứ<ừ<ử<ữ" +
                "<v<w<x<y<ý<ỳ<ỷ<ỹ<ỵ<z";

        try {
            RuleBasedCollator collator = new RuleBasedCollator(rules);
            students.sort((s1, s2) -> {
                int nameCompare = collator.compare(s1.getFirstName().toLowerCase(),
                        s2.getFirstName().toLowerCase());
                if (nameCompare != 0) {
                    return nameCompare;
                } else {
                    return collator.compare(s1.getLastName().toLowerCase(),
                            s2.getLastName().toLowerCase());
                }
            });
        } catch (Exception e) {
            Log.e("ParseException", Objects.requireNonNull(e.getMessage()));
        }
    }

    @Override
    public void sortByNameDESC(List<Student> students) {
        sortByNameASC(students);
        Collections.reverse(students);
    }

    @Override
    public void sortByGpaASC(List<Student> students) {
        students.sort((s1, s2) -> Float.compare(s1.getGpa(), s2.getGpa()));
    }

    @Override
    public void sortByGpaDESC(List<Student> students) {
        students.sort((s1, s2) -> Float.compare(s2.getGpa(), s1.getGpa()));
    }

    @Override
    public void sortByIdASC(List<Student> students) {
        students.sort((s1,s2)->{
            String id1Str = s1.getId();
            String id2Str = s2.getId();
            String id1 = id1Str.substring(id1Str.length()-3);
            String id2 = id2Str.substring(id2Str.length()-3);
            return Integer.parseInt(id1) - Integer.parseInt(id2);
        });
    }

    @Override
    public void sortByIdDESC(List<Student> students) {
        sortByIdASC(students);
        Collections.reverse(students);
    }
}

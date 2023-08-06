package com.hashem.p1;

import com.hashem.p1.models.CClass;
import com.hashem.p1.models.Grade;

import java.util.List;

public record ClassGradesPair(CClass clazz, List<Grade> grades) {
}

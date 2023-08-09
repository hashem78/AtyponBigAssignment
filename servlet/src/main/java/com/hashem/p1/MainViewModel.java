package com.hashem.p1;

import com.hashem.p1.models.CClass;
import com.hashem.p1.models.ClassStatistics;
import com.hashem.p1.models.Grade;

import java.util.List;

public record MainViewModel(CClass clazz, List<Grade> grades, ClassStatistics statistics) {
}

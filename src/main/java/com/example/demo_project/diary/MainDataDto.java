package com.example.demo_project.diary;

import com.example.demo_project.diary.category.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MainDataDto {

    private List<Category> categoryList;

}

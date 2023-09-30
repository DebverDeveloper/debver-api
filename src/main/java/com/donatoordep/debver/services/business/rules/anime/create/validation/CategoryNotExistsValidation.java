package com.donatoordep.debver.services.business.rules.anime.create.validation;

import com.donatoordep.debver.dto.request.CategoriesRequestDTO;
import com.donatoordep.debver.entities.Categories;
import com.donatoordep.debver.enums.Category;
import com.donatoordep.debver.repositories.CategoriesRepository;
import com.donatoordep.debver.services.business.rules.anime.create.CreateAnimeArgs;
import com.donatoordep.debver.services.business.rules.anime.create.CreateAnimeValidation;
import com.donatoordep.debver.services.exceptions.InvalidEnumValueException;
import com.donatoordep.debver.utils.ConvertingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryNotExistsValidation implements CreateAnimeValidation {

    @Autowired
    CategoriesRepository repository;

    @Override
    public void validation(CreateAnimeArgs args) {
        List<Categories> all = repository.findAll();
        List<Category> categoryList = all.stream().map(Categories::getCategory).toList();

        List<String> invalidCategories = args.dto().getCategories().stream()
                .map(CategoriesRequestDTO::getCategory)
                .filter(category -> all.stream().noneMatch(c -> c.getCategory().name().equals(category)))
                .toList();

        boolean categoryNotExists = args.dto().getCategories().stream()
                .noneMatch(category -> all.stream()
                        .anyMatch(c -> c.getCategory().name().equals(category.getCategory())));

        if (categoryNotExists) {
            throw new InvalidEnumValueException(invalidCategories.toString(), Category.class.toString(),
                    ConvertingType.listToString(categoryList));
        }
    }
}
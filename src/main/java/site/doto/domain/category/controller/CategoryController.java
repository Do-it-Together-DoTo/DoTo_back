package site.doto.domain.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.doto.domain.category.dto.*;
import site.doto.domain.category.service.CategoryService;
import site.doto.global.dto.ResponseDto;

import javax.validation.Valid;

import static site.doto.global.status_code.SuccessCode.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseDto<CategoryDetailsRes> categoryAdd(
            @RequestBody @Valid CategoryAddReq categoryAddReq) {
        Long memberId = 1L;

        CategoryDetailsRes result = categoryService.addCategory(memberId, categoryAddReq);

        return ResponseDto.success(CATEGORY_CREATED, result);
    }

    @GetMapping
    public ResponseDto<CategoryListRes> categoryList() {
        Long memberId = 1L;

        CategoryListRes result = categoryService.findCategory(memberId);

        return ResponseDto.success(CATEGORIES_INQUIRY_OK, result);
    }

    @PatchMapping("/{categoryId}")
    public ResponseDto<CategoryDetailsRes> categoryModify(
            @PathVariable long categoryId,
            @RequestBody CategoryModifyReq categoryModifyReq) {
        Long memberId = 1L;

        CategoryDetailsRes result = categoryService.modifyCategory(memberId, categoryId, categoryModifyReq);

        return ResponseDto.success(CATEGORY_MODIFY_OK, result);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseDto<?> categoryRemove(
            @PathVariable long categoryId) {
        Long memberId = 1L;

        categoryService.removeCategory(memberId, categoryId);

        return ResponseDto.success(CATEGORY_DELETED, null);
    }

    @PatchMapping
    public ResponseDto<?> categoryArrange(
            @RequestBody CategoryArrangeReq categoryArrangeReq) {
        Long memberId = 1L;

        categoryService.arrangeCategory(memberId, categoryArrangeReq);

        return ResponseDto.success(CATEGORY_ARRANGE_OK, null);
    }
}
package site.doto.domain.todo.dto;

import lombok.Builder;
import lombok.Data;
import site.doto.domain.todo.entity.Todo;

import java.io.Serializable;

@Data
@Builder
public class TodoRedisDto implements Serializable {
    private Long bettingId;

    private Long todoId;

    private Long memberId;

    private Long categoryId;

    private String categoryContents;

    private String contents;

    public static TodoRedisDto toDto(Todo todo, Long id) {
        return TodoRedisDto.builder()
                .bettingId(id)
                .todoId(todo.getId())
                .memberId(todo.getMember().getId())
                .categoryId(todo.getCategory().getId())
                .contents(todo.getContents())
                .categoryContents(todo.getCategory().getContents())
                .build();
    }
}

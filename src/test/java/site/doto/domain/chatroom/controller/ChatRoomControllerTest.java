package site.doto.domain.chatroom.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static site.doto.global.status_code.SuccessCode.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class ChatRoomControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    private final static String jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    @Test
    @DisplayName("참여 중인 채팅방 조회_성공")
    public void chatRoomList_success() throws Exception {
        //given

        //when
        ResultActions actions = mockMvc.perform(
                get("/chatting")
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(CHATROOMS_INQUIRY_OK.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(CHATROOMS_INQUIRY_OK.getMessage()))
                .andDo(document(
                        "참여 중인 채팅방 조회",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Chatting API")
                                .summary("참여 중인 채팅방 조회 API")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰")
                                )
                                .responseFields(
                                        List.of(
                                                fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                        .description("성공 코드"),
                                                fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                        .description("성공 메시지"),
                                                fieldWithPath("body.chatRooms").type(JsonFieldType.ARRAY)
                                                        .description("참여 중인 채팅방 목록"),
                                                fieldWithPath("body.chatRooms[].chatRoomId").type(JsonFieldType.NUMBER)
                                                        .description("채팅방 ID"),
                                                fieldWithPath("body.chatRooms[].bettingId").type(JsonFieldType.NUMBER)
                                                        .description("베팅 ID"),
                                                fieldWithPath("body.chatRooms[].bettingName").type(JsonFieldType.STRING)
                                                        .description("베팅 이름"),
                                                fieldWithPath("body.chatRooms[].memberId").type(JsonFieldType.NUMBER)
                                                        .description("멤버 ID"),
                                                fieldWithPath("body.chatRooms[].memberNickname").type(JsonFieldType.STRING)
                                                        .description("멤버 닉네임"),
                                                fieldWithPath("body.chatRooms[].mainCharacterImg").type(JsonFieldType.STRING)
                                                        .description("메인 캐릭터 이미지 주소")
                                        )
                                )
                                .responseSchema(Schema.schema("참여 중인 채팅방 조회 Response"))
                                .build()
                        ))
                );
    }

    @Test
    @DisplayName("채팅방 참여_성공")
    public void chatRoomJoin_success() throws Exception {
        //given
        Long chatRoomId = 30001L;

        //when
        ResultActions actions = mockMvc.perform(
                post("/chatting/{chatRoomId}", chatRoomId)
                        .header("Authorization", jwtToken)
                        .accept(MediaType.APPLICATION_JSON));

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.header.httpStatusCode").value(MEMBER_CHATROOM_CREATED.getHttpStatusCode()))
                .andExpect(jsonPath("$.header.message").value(MEMBER_CHATROOM_CREATED.getMessage()))
                .andDo(document(
                        "채팅방 참여",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("Chatting API")
                                .summary("채팅방 참여 API")
                                .requestHeaders(
                                        headerWithName("Authorization")
                                                .description("JWT 토큰")
                                )
                                .pathParameters(
                                        parameterWithName("chatRoomId")
                                                .description("채팅방 ID")
                                )
                                .responseFields(
                                        List.of(
                                                fieldWithPath("header.httpStatusCode").type(JsonFieldType.NUMBER)
                                                        .description("성공 코드"),
                                                fieldWithPath("header.message").type(JsonFieldType.STRING)
                                                        .description("성공 메시지"),
                                                fieldWithPath("body").type(JsonFieldType.NULL)
                                                        .description("내용 없음")
                                        )
                                )
                                .requestSchema(Schema.schema("채팅방 참여 Request"))
                                .responseSchema(Schema.schema("채팅방 참여 Response"))
                                .build()
                        ))
                );
    }
}

package site.doto.domain.betting.dto;

import lombok.Data;
import site.doto.domain.betting.entity.Betting;
import site.doto.domain.member_betting.entity.MemberBetting;

import java.time.LocalDate;
import java.util.List;

@Data
public class BettingDetailsRes {
    private Long bettingId;

    private String bettingName;

    private Long chatRoomId;

    private Long memberId;

    private String memberNickname;

    private String todoContents;

    private Integer successCoins;

    private Integer failureCoins;

    private Integer participantCount;

    private Boolean isParticipating;

    private Boolean isFinished;

    private Boolean isAchieved;

    public BettingDetailsRes(Betting betting, List<MemberBetting> memberBetting, Long memberId) {
        this.bettingId = betting.getId();
        this.bettingName = betting.getName();
        this.memberId = betting.getMember().getId();
        this.memberNickname = betting.getMember().getNickname();
        this.todoContents = betting.getTodo().getContents();
        this.chatRoomId = betting.getChatRoom().getId();
        this.isAchieved = betting.getIsAchieved();
        this.isFinished = betting.getDate().isBefore(LocalDate.now());
        this.participantCount = memberBetting.size();
        this.successCoins = 0;
        this.failureCoins = 0;
        this.isParticipating = false;

        memberBetting
                .forEach(mb -> {
                    if (mb.getPrediction()) {
                        successCoins += mb.getCost();
                    } else {
                        failureCoins += mb.getCost();
                    }

                    if (mb.getMember().getId().equals(memberId)) {
                        isParticipating = true;
                    }
                });
    }
}

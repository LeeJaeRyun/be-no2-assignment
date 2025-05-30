package com.example.schedule.service;

import com.example.exception.ApplicationException;
import com.example.exception.PasswordMismatchException;
import com.example.schedule.dto.request.MemberSaveRequestDto;
import com.example.schedule.dto.request.MemberUpdateRequestDto;
import com.example.schedule.dto.response.MemberResponseDto;
import com.example.schedule.entity.Member;
import com.example.schedule.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponseDto save(MemberSaveRequestDto requestDto) {
        if (memberRepository.existByEmail(requestDto.getEmail())) {
            throw new ApplicationException("이미 존재하는 이메일입니다.", HttpStatus.BAD_REQUEST);
        }

        Member member = new Member(
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );

        Member savedMember = memberRepository.save(member);

        return new MemberResponseDto(
                savedMember.getId(),
                savedMember.getName(),
                savedMember.getEmail(),
                savedMember.getCreatedAt(),
                savedMember.getUpdatedAt()
        );
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new ApplicationException("해당 id의 회원이 존재하지 않습니다.", HttpStatus.NOT_FOUND)
        );

        return new MemberResponseDto(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getCreatedAt(),
                member.getUpdatedAt()
        );
    }

    @Transactional
    public MemberResponseDto update(Long id, MemberUpdateRequestDto requestDto) {

        Member member = memberRepository.findById(id).orElseThrow(
                () -> new ApplicationException("해당 id의 회원이 존재하지 않습니다.", HttpStatus.NOT_FOUND)
        );

        member.update(requestDto.getName(), requestDto.getPassword());

        Member updatedMember = memberRepository.update(id, member);

        return new MemberResponseDto(
                updatedMember.getId(),
                updatedMember.getName(),
                updatedMember.getEmail(),
                updatedMember.getCreatedAt(),
                updatedMember.getUpdatedAt()
        );
    }

    @Transactional
    public void delete(Long id, String password) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new ApplicationException("해당 id의 회원이 존재하지 않습니다.", HttpStatus.NOT_FOUND)
        );

        if (!password.equals(member.getPassword())) {
            throw new PasswordMismatchException();
        }

        memberRepository.deleteById(id);
    }
}

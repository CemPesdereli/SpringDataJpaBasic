package com.cem.datajpabasic.student;

public record StudentResponseDto(
        String firstname,
        String lastname,
        String email
) {
}

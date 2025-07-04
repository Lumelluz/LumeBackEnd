package br.com.lume.lumemarketplace.question;

import jakarta.validation.constraints.NotBlank;

public record AnswerDTO(@NotBlank String answerText) {}
import { useState } from "react";
import SeaSrc from "assets/바다.jpg";
import styled from "styled-components";

interface AnswerProps {
	number: number;
	answerHandleChange: (answer: number) => void;
}

function Answer({ number, answerHandleChange }: AnswerProps) {
	const answerHandleClick = () => answerHandleChange(number);

	return (
		<Container onClick={answerHandleClick}>
			<SurveyImg src={SeaSrc} />
			<Content>바다</Content>
		</Container>
	);
}

const Container = styled.div`
	width: 20rem;
	display: flex;
	flex-direction: column;
	margin: 4px;
`;

const SurveyImg = styled.img`
	background-color: none;
	border: none;
`;

const Content = styled.p`
	font-size: 2.5rem;
	text-align: center;
	margin: 0;
`;

export default Answer;

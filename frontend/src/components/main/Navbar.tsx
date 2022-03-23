import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import styled from "styled-components";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowRightFromBracket, faBell, faUser } from "@fortawesome/free-solid-svg-icons";
import Search from "./Search";

interface NavProps {
	justifyContent?: string;
}

interface HeaderProps {
	scrollHeader: number;
}

function Navbar() {
	const [scrollPosition, setScrollPosition] = useState(0);
	const updateScroll = () => {
		setScrollPosition(window.scrollY || document.documentElement.scrollTop);
	};

	useEffect(() => {
		window.addEventListener("scroll", updateScroll);
	});

	return (
		<Header scrollHeader={scrollPosition}>
			<Nav>
				<Ul>
					<ListItem>
						<LinkParagraph scrollHeader={scrollPosition} to="/recommend">
							RECOMMEND
						</LinkParagraph>
						<LinkParagraph scrollHeader={scrollPosition} to="/">
							PERFUMES
						</LinkParagraph>
						<LinkParagraph scrollHeader={scrollPosition} to="/">
							COMMUNITY
						</LinkParagraph>
					</ListItem>
				</Ul>
			</Nav>
			<Head scrollHeader={scrollPosition} to="/">
				PERFUMISM
			</Head>
			<Nav justifyContent="flex-end">
				<Ul>
					<ListItem>
						<Search />
						<FontAwesome icon={faBell} />
						<FontAwesome icon={faUser} />
						<FontAwesome icon={faArrowRightFromBracket} />
					</ListItem>
				</Ul>
			</Nav>
		</Header>
	);
}

const Header = styled.header<HeaderProps>`
	background: ${({ scrollHeader }) => (scrollHeader > 2 ? "#000" : "#fff")};
	color: ${({ scrollHeader }) => (scrollHeader > 2 ? "#fff" : "#000")};
	width: 100%;
	height: 10rem;
	position: fixed;
	z-index: 20;
	display: flex;
	justify-content: space-between;
	align-items: center;
	border-bottom: 1px solid black;
`;

const Nav = styled.nav<NavProps>`
	display: flex;
	justify-content: ${({ justifyContent }) => (justifyContent ? justifyContent : "flex-start")};
	width: 20%;
	margin: 0 5%;
`;

const Ul = styled.ul`
	display: flex;
`;

const ListItem = styled.li`
	display: flex;
	justify-content: space-between;
	align-items: center;
`;

const LinkParagraph = styled(Link)<HeaderProps>`
	text-decoration: none;
	font-size: 1.3rem;
	font-weight: 900;
	margin-right: 5rem;
	color: ${({ scrollHeader }) => (scrollHeader > 2 ? "#fff" : "#000")};
`;

const Head = styled(Link)<HeaderProps>`
	font-size: 4rem;
	font-weight: 900;
	text-decoration: none;
	display: flex;
	justify-content: center;
	color: ${({ scrollHeader }) => (scrollHeader > 2 ? "#fff" : "#000")};
`;

const FontAwesome = styled(FontAwesomeIcon)`
	width: 1.8rem;
	height: 1.8rem;
	margin-left: 2rem;
	cursor: pointer;
`;
export default Navbar;
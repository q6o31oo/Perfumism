import PerfumeImage from "./PerfumeImage";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import LikeButton from "components/perfume/LikeButton";
import cookie from "react-cookies";

interface PerfumeList {
	perfumes: Perfume[];
}

interface Perfume {
	perfume_id: string;
	perfume_name: string;
	brand_name?: string;
	image: string;
}

function PerfumeList({ perfumes }: PerfumeList) {
	const token = cookie.load("access_token");

	const handlePerfumeItemClick = (perfumeId: string) => {
		window.location.replace(`/perfume/${perfumeId}`);
	};

	return (
		<Container>
			{perfumes.length > 0 &&
				perfumes.map((perfume: Perfume, idx: number) => (
					<Perfume key={idx}>
						<PerfumeItem onClick={() => handlePerfumeItemClick(perfume.perfume_id)}>
							<PerfumeImage
								src={`https://fimgs.net/mdimg/perfume/375x500.${perfume.image}`}
								alt="perfume image"
							/>
							<Name>{perfume.perfume_name}</Name>
						</PerfumeItem>
						{token && <LikeButton center perfumeId={perfume.perfume_id} />}
					</Perfume>
				))}
		</Container>
	);
}

const Container = styled.ul`
	display: flex;
	flex-wrap: wrap;
`;

const Perfume = styled.li`
	width: 15%;
	margin: 3rem;
	height: auto;
	list-style: none;
	font-size: 1.4rem;
	position: relative;
	&:hover {
		button {
			display: block;
		}
	}
`;

const PerfumeItem = styled.div`
	cursor: pointer;
`;

const Name = styled.p`
	text-align: center;
`;

export default PerfumeList;

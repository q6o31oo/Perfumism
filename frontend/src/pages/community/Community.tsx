import { useEffect, useState } from "react";
import { MainHeader, CommunityList } from "components/community";
import Pagination from "components/Pagination";
import communityApi from "apis/community";
import styled from "styled-components";

interface ArticleList {
	article_id: number;
	member_id: number;
	member_name: string;
	subject: string;
	title: string;
	content: string;
	created_at: string;
	updated_at: string;
	deleted_at: string;
}

interface ArticleDataType {
	articleList?: ArticleList[];
	total_page_count: number;
	current_page_count: number;
}

function Community() {
	const [articleDataList, setArticleDataList] = useState<ArticleDataType>({
		articleList: [],
		total_page_count: 0,
		current_page_count: 0,
	});
	const { articleList, total_page_count, current_page_count } = articleDataList;
	const [subject, setSubject] = useState("전체");

	useEffect(() => {
		getArticleDataList();
	}, [current_page_count]);

	useEffect(() => {
		getArticleDataList(0);
	}, [subject]);

	const getArticleDataList = async (resetCnt?: number) => {
		if (subject === "전체") {
			try {
				const res = await communityApi.getCommunityList(current_page_count);
				setArticleDataList(res.data);
			} catch (error) {
				console.log(error);
			}
		} else {
			try {
				if (resetCnt === 0) {
					const res = await communityApi.getSubjectCommunityList(resetCnt, subject);
					setArticleDataList(res.data);
				} else {
					const res = await communityApi.getSubjectCommunityList(current_page_count, subject);
					setArticleDataList(res.data);
				}
			} catch (error) {
				console.log(error);
			}
		}
	};

	return (
		<Container>
			<MainHeader setSubject={setSubject} />
			<CommunityList articleList={articleList} />
			{articleDataList.articleList?.length && (
				<Pagination
					page={current_page_count}
					total={total_page_count}
					setData={setArticleDataList}
				/>
			)}
		</Container>
	);
}

const Container = styled.div`
	height: 80vh;
	width: 50%;
	display: flex;
	flex-direction: column;
	align-items: center;
	margin: 0 25%;
`;

export default Community;

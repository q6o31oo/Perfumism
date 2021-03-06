import { AxiosResponse } from "axios";
import { djangoRequest } from "./request";

interface RecommendApiType {
	surveyRecommend: (
		a1: number,
		a2: number,
		a3: number,
		a4: number,
		a5: number,
	) => Promise<AxiosResponse>;
	likeBasedRecommend: (id: number) => Promise<AxiosResponse>;
}

const recommendApi: RecommendApiType = {
	surveyRecommend: (a1, a2, a3, a4, a5) =>
		djangoRequest.get(`algorithms/survey/${a1}/${a2}/${a3}/${a4}/${a5}`),
	likeBasedRecommend: (id) => djangoRequest.get(`algorithms/${id}/like-based`),
};

export default recommendApi;

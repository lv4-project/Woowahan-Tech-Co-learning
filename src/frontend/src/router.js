import Vue from 'vue'
import VueRouter from "vue-router";
import SignUpForm from "./components/user/SignUpForm";
import StudyGeneration from "./components/study/StudyGeneration";
import StudyRecruitment from "./components/study/StudyRecruitment";
import StudyOngoing from "./components/study/StudyOngoing";
import StudyFinished from "./components/study/StudyFinished";
import UserProfile from "./components/user/UserProfile";
import ParticipatingStudy from "./components/user/ParticipatingStudy";
import UserUpdateForm from "./components/user/UserUpdateForm";

Vue.use(VueRouter);

const routes = [
  {
    name: `SignUpForm`,
    path: `/SignUpForm`,
    component: SignUpForm,
  },
  {
    name: `StudyGeneration`,
    path: `/StudyGeneration`,
    component: StudyGeneration,
  },
  {
    name: `StudyRecruitment`,
    path: `/StudyRecruitment`,
    component: StudyRecruitment,
  },
  {
    name: `StudyOngoing`,
    path: `/StudyOngoing`,
    component: StudyOngoing,
  },
  {
    name: `StudyFinished`,
    path: `/StudyFinished`,
    component: StudyFinished,
  },
  {
    name: `UserProfile`,
    path: `/UserProfile`,
    component: UserProfile,
  },
  {
    name: `ParticipatingStudy`,
    path: `/ParticipatingStudy`,
    component: ParticipatingStudy,
  },
  {
    name: `UserUpdateForm`,
    path: `/UserUpdateForm`,
    component: UserUpdateForm,
  },
];

export default new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: routes,
});
<script setup lang="ts">
import { type Ballot } from '@/entities/ballot';
import BallotsService from '@/services/ballots-service';
import { onMounted, reactive, type PropType } from 'vue';
import VotesService from '@/services/votes-service';
import type { Vote } from '@/entities/vote';
import VotesColumn from './VotesColumn.vue';
import type { Deputy } from '@/entities/deputy';
import DeputiesService from '@/services/deputies-service';
import type { Mandate } from '@/entities/mandate';
import MandatesService from '@/services/mandates-service';
import _ from 'lodash'
import DeputiesUtils from '@/utils/deputies-utils';
import type { PoliticalGroupVotes } from '@/entities/political-group-votes';
import AuthoritiesService from '@/services/authorities-service';
import VotesUtils from '@/utils/votes-utils';

const ballotsService = new BallotsService();
const votesService = new VotesService();
const authoritiesService = new AuthoritiesService();

const props = defineProps({
  ballot: {
    type: Object as PropType<Ballot>,
    required: true
  }
});

const votes = reactive<{
  data: Map<string, Vote[]>
  isLoading: boolean
}>({
  data: new Map([
    ["nonVotant", []],
    ["pour", []],
    ["contre", []],
    ["abstention", []],
    ["nonVotantVolontaire", []],
  ]),
  isLoading: true
});

onMounted(
  async () => {
    try {
      const allVotes: Vote[] = await votesService.getVotes()
      votes.data.forEach((value: Vote[], key: string) => {
        votes.data.set(key, allVotes.filter((vote) => vote.ballot.id === props.ballot.id && vote.state === key))
      });
    } catch (error) {
      console.error('Error fetching votes', error)
    }
  }
);

const deputiesService = new DeputiesService();
const deputies = reactive<{
  data: Deputy[]
  isLoading: boolean
}>({
  data: [],
  isLoading: true
});

onMounted(
  async () => {
    deputies.data = await deputiesService.getDeputies()
    deputies.isLoading = false

  }
);

const mandatesService = new MandatesService();
const tableVotesNumber = reactive<{
  data: PoliticalGroupVotes[]
  isLoading: boolean
}>({
  data: [],
  isLoading: true
});

onMounted(
  async () => {
    const deputies = await deputiesService.getDeputies()
    const authorities = await authoritiesService.getAuthorities()
    const mandates: Mandate[] = await mandatesService.getMandates()
    const votes: Vote[] = await votesService.getVotes()

    const authoritiesById = _.groupBy(authorities, (authority) => authority.id)

    const ballotVotes = _.groupBy(votes, (vote) => vote.ballot.id)[props.ballot.id]

    const activeDeputiesByPGId = DeputiesUtils.getActiveDeputiesByPGIdForSpecificDate(
      deputies,
      mandates,
      props.ballot.ballotDate
    )

    const ballotVotesByDeputyId = _.groupBy(ballotVotes, (vote) => vote.deputy.id)

    tableVotesNumber.data = _.map(activeDeputiesByPGId, (value, key) => {
      const politicalGroupVotes: PoliticalGroupVotes = {
        politicalGroupLabel: authoritiesById[key][0].label,
        nonVoting: VotesUtils.getDeputiesVotesNumberForVoteState(
          value,
          ballotVotesByDeputyId,
          "nonVotant"
        ),
        for: VotesUtils.getDeputiesVotesNumberForVoteState(
          value,
          ballotVotesByDeputyId,
          "pour"
        ),
        against: VotesUtils.getDeputiesVotesNumberForVoteState(
          value,
          ballotVotesByDeputyId,
          "contre"
        ),
        abstention: VotesUtils.getDeputiesVotesNumberForVoteState(
          value,
          ballotVotesByDeputyId,
          "abstention"
        )
      }
      return politicalGroupVotes
    })
  }
);
</script>

<template>
  <div class="grid grid-cols-1 gap-3 p-3 rounded-xl bg-white shadow-md outline outline-black/5">
    <div class="text-base text-gray-700">
      <p>{{ ballotsService.capitalizeFirstLetter(ballot.title) }}</p>
      {{ tableVotesNumber }}
    </div>
    <div class="flex flex-row gap-x-2 items-baseline">
      <i class="text-sm text-gray-500 pi pi-calendar-minus"></i>
      <p class="text-sm text-gray-500 p-0">{{ ballot.ballotDate }}</p>
    </div>
    <div class="flex flex-row flex-wrap justify-around gap-5">
      <VotesColumn v-for="[key, value] of votes.data" :key="key" :votes="value" :name=key />
    </div>
  </div>
</template>

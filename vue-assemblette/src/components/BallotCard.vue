<script setup lang="ts">
import { type Ballot } from '@/entities/ballot';
import BallotsService from '@/services/ballots-service';
import { onMounted, reactive, type PropType } from 'vue';
import VotesService from '@/services/votes-service';
import type { Vote } from '@/entities/vote';
import VotesColumn from './VotesColumn.vue';

const ballotsService = new BallotsService();
const votesService = new VotesService();

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
</script>

<template>
  <div class="grid grid-cols-1 gap-3 p-3 rounded-xl bg-white shadow-md outline outline-black/5">
    <div class="text-base text-gray-700">
      <p>{{ ballotsService.capitalizeFirstLetter(ballot.title) }}</p>
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

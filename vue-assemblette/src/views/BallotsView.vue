<script setup lang="ts">
import { reactive, onMounted } from 'vue';
import { type Ballot } from '@/entities/ballot';
import { type Vote } from '@/entities/vote';
import BallotsService from '@/services/ballots-service';
import VotesService from '@/services/votes-service';

const ballotsService = new BallotsService();
const votesService = new VotesService();

const ballots = reactive<{
  data: Ballot[],
  isLoading: boolean
}>({
  data: [],
  isLoading: true
});

const votes = reactive<{
  data: Vote[],
  isLoading: boolean
}>({
  data: [],
  isLoading: true
});

onMounted(
  async () => {
    try {
      ballots.data = await ballotsService.getBallots()
    } catch (error) {
      console.error('Error fetching ballots', error)
    }
  }
);

onMounted(
  async () => {
    try {
      votes.data = await votesService.getVotes()
    } catch (error) {
      console.error('Error fetching votes', error)
    }
  }
);

</script>

<template>
  <div v-if="ballots.data.length != 0"
    class="flex flex-col gap-y-1 gap-x-2 rounded-xl bg-white m-6 p-3 shadow-lg outline outline-black/5">
    <div class="text-lg text-gray-700">
      <p>{{ ballotsService.capitalizeFirstLetter(ballots.data[0].title) }}</p>
    </div>
    <div class="flex flex-row gap-x-2 items-top">
      <p>{{ ballots.data[0].ballotDate }}</p>
    </div>
    <div class="flex flex-row gap-x-2 items-top">
      <p>{{ votes.data[0] }}</p>
    </div>
  </div>
</template>

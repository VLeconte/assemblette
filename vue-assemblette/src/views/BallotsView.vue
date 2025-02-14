<script setup lang="ts">
import { reactive, onMounted } from 'vue';
import BallotCard from '@/components/BallotCard.vue';
import { type Ballot } from '@/entities/ballot';
import BallotsService from '@/services/ballots-service';

const ballotsService = new BallotsService();

const ballots = reactive<{
  data: Ballot[],
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

</script>

<template>
  <div>
    <BallotCard v-for="ballot in ballots.data" :key="ballot.id" :ballot="ballot" />
  </div>
</template>

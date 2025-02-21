<script setup lang="ts">
import { reactive, ref, onMounted } from 'vue';
import BallotCard from '@/components/BallotCard.vue';
import { type Ballot } from '@/entities/ballot';
import BallotsService from '@/services/ballots-service';
import MandatesService from '@/services/mandates-service';
import type { Mandate } from '@/entities/mandate';
import _ from 'lodash';

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
      ballots.isLoading = false
    } catch (error) {
      console.error('Error fetching ballots', error)
    }
  }
);

const mandatesService = new MandatesService();
const mandatesByDeputies = ref<Record<string, Mandate[]>>({})

onMounted(
  async () => {
    try {
      const mandates: Mandate[] = await mandatesService.getMandates()
      mandatesByDeputies.value = _.groupBy(mandates, (mandate) => mandate.deputy.id)
    } catch (error) {
      console.error('Error fetching mandates', error)
    }
  }
);

</script>

<template>
  <div class="w-screen grid grid-cols-1 gap-8 p-4 justify-center">
    <BallotCard v-for="ballot in ballots.data.slice(0, 3)" :key="ballot.id" :ballot="ballot"
      :mandatesByDeputies="mandatesByDeputies" />
  </div>
</template>

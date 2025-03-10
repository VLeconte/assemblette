<script setup lang="ts">
import { onMounted, ref } from 'vue';
import BallotCard from '@/components/BallotCard.vue';
import { type Ballot } from '@/entities/ballot';
import _ from 'lodash';
import { useDeputiesStore } from '@/store/store-deputies';
import { useVotesStore } from '@/store/store-votes';
import { useAuthoritiesStore } from '@/store/store-authorities';
import { useMandatesStore } from '@/store/store-mandates';
import { useBallotsStore } from '@/store/store-ballots';
import PaginatorAssemblette from '@/components/PaginatorAssemblette.vue';

const deputiesStore = useDeputiesStore()
const votesStore = useVotesStore()
const authoriesStore = useAuthoritiesStore()
const mandatesStore = useMandatesStore()
const ballotsStore = useBallotsStore()

const ballotsOrdered = ref<Ballot[]>([])

const loadingData = ref(true)
const paginatorFirstRow = ref(0)

onMounted(
  async () => {
    await deputiesStore.getDeputies()
    await authoriesStore.getAuthorities()
    await mandatesStore.getMandates()
    await votesStore.getVotes()
    const ballots = await ballotsStore.getBallots()
    ballotsOrdered.value = _.reverse(_.sortBy(ballots, (ballot) => ballot.ballotDate))
    loadingData.value = false
  }
);

</script>

<template>
  <div v-if="loadingData" class="flex flex-row items-baseline justify-center gap-2 p-8">
    <i class="text-xl text-gray-500 pi pi-spin pi-spinner"></i>
    <p class="text-xl text-gray-500">Loading data</p>
  </div>
  <div v-else class="w-screen grid grid-cols-1 gap-8 p-4 justify-center">
    <BallotCard v-for="ballot in ballotsOrdered.slice(paginatorFirstRow, paginatorFirstRow + 5)" :key="ballot.id"
      :ballot="ballot" />
    <PaginatorAssemblette v-model="paginatorFirstRow" :rows="5" :size="ballotsOrdered.length" :pages-selectable="3" />
  </div>
</template>

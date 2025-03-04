<script setup lang="ts">
import { type Ballot } from '@/entities/ballot';
import { onMounted, reactive, ref, type PropType } from 'vue';
import _ from 'lodash'
import DeputiesUtils from '@/utils/deputies-utils';
import type { PoliticalGroupVotes } from '@/entities/political-group-votes';
import VotesUtils from '@/utils/votes-utils';
import TableVotes from '@/components/TableVotes.vue';
import { useVotesStore } from '@/store/store-votes';
import { useAuthoritiesStore } from '@/store/store-authorities';
import { useDeputiesStore } from '@/store/store-deputies';
import { useMandatesStore } from '@/store/store-mandates';
import { capitalizeFirstLetter } from '@/utils/mandates-utils';

const deputiesStore = useDeputiesStore()
const votesStore = useVotesStore()
const authoriesStore = useAuthoritiesStore()
const mandatesStore = useMandatesStore()

const props = defineProps({
  ballot: {
    type: Object as PropType<Ballot>,
    required: true
  }
});

const showTable = ref(false);

const toggleShowTable = () => {
  showTable.value = !showTable.value
}

const tableVotesNumber = reactive<{
  data: PoliticalGroupVotes[]
  isLoading: boolean
}>({
  data: [],
  isLoading: true
});

onMounted(
  async () => {
    const deputies = await deputiesStore.getDeputies()
    const authorities = await authoriesStore.getAuthorities()
    const mandates = await mandatesStore.getMandates()
    const votes = await votesStore.getVotes()

    const authoritiesById = _.keyBy(authorities, (authority) => authority.id)
    const ballotVotes = _.groupBy(votes, (vote) => vote.ballot.id)[props.ballot.id]

    const activeDeputiesByPGId = DeputiesUtils.getActiveDeputiesByPGIdForSpecificDate(
      deputies,
      mandates,
      props.ballot.ballotDate
    )

    const ballotVotesByDeputyId = _.groupBy(ballotVotes, (vote) => vote.deputy.id)

    tableVotesNumber.data = _.map(activeDeputiesByPGId, (value, key) => {
      const politicalGroupVotes: PoliticalGroupVotes = {
        politicalGroupLabel: key != "undefined" ? authoritiesById[key].label : "Inconnu",
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
      <p>{{ capitalizeFirstLetter(ballot.title) }}</p>
    </div>
    <div class="flex flex-row gap-x-2 items-baseline">
      <i class="text-sm text-gray-500 pi pi-calendar-minus"></i>
      <p class="text-sm text-gray-500 p-0">{{ ballot.ballotDate }}</p>
    </div>
    <button @click="toggleShowTable" class="flex flex-row gap-x-2 items-baseline text-sm text-gray-500">
      <i class="pi text-inherit" :class="showTable ? 'pi-chevron-up' : 'pi-chevron-down'"></i>
      <div class="text-inherit">
        <p>{{ showTable ? "Voir moins" : "Voir plus" }}</p>
      </div>
    </button>
    <TableVotes v-if="showTable" :political-groups-votes="tableVotesNumber.data" />
  </div>
</template>

import axios from 'axios'

const restDeputies = axios.create({
  // better still, use env vars to define your URLs
  baseURL: 'http://localhost:8080/api/deputies',
})

export default class DeputiesService {
  public getDeputies = async () => {
    try {
      return (await restDeputies.get('')).data
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        console.error(err.toJSON())
        throw new Error(err.message)
      } else {
        console.error(err)
        throw new Error('An unexpected error occurred')
      }
    }
  }
}
